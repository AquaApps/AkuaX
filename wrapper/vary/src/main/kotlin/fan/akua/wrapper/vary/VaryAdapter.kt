package fan.akua.wrapper.vary


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class VaryAdapter : RecyclerView.Adapter<VaryAdapter.BindingViewHolder>() {

    private var onBind: (BindingViewHolder.() -> Unit)? = null

    private var onCreate: (ViewCreate.() -> Unit)? = null

    private val allList: List<Any>
        get() {
            val list = mutableListOf<Any>()
            headers?.let { list.addAll(it) }
            models?.let { list.addAll(it) }
            footers?.let { list.addAll(it) }
            return list
        }

    /**
     * list of header items
     * The data in this list will be displayed before [models], [footers]
     */
    var headers: List<Any>? = null
        set(value) {
            value ?: return
            val list = mutableListOf<Any>()
            list.addAll(value)
            models?.let { list.addAll(it) }
            footers?.let { list.addAll(it) }
            val diffResult =
                DiffUtil.calculateDiff(
                    AdapterDiffCallback(
                        allList,
                        list
                    ), true
                )
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    /**
     * list of default items
     * The data in this list will be displayed before [footers],after[headers]
     */
    var models: List<Any>? = null
        set(value) {
            value ?: return
            val list = mutableListOf<Any>()
            headers?.let { list.addAll(it) }
            list.addAll(value)
            footers?.let { list.addAll(it) }
            val diffResult =
                DiffUtil.calculateDiff(
                    AdapterDiffCallback(
                        allList,
                        list
                    ), true
                )
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    /**
     * list of footer items
     * The data in this list will be displayed after [headers],[models]
     */
    var footers: List<Any>? = null
        set(value) {
            value ?: return
            val list = mutableListOf<Any>()
            headers?.let { list.addAll(it) }
            models?.let { list.addAll(it) }
            list.addAll(value)
            val diffResult =
                DiffUtil.calculateDiff(
                    AdapterDiffCallback(
                        allList,
                        list
                    ), true
                )
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    /**
     *Store the temporary variable of the model corresponding to [BindingViewHolder]
     */
    var mModel: Any? = null

    /**
     * [block] will be called when [onBindViewHolder]
     * @see [onBindViewHolder]
     */
    fun onBind(block: BindingViewHolder.() -> Unit) {
        onBind = block
    }

    /**
     * Called when the root of the [BindingViewHolder] is created
     * @see [onCreateViewHolder]
     */
    fun onCreate(block: ViewCreate.() -> Unit) {
        onCreate = block
    }

    /**
     * Store the model type added in [addType] and its corresponding layout id
     * @see [addType]
     * @see [getItemViewType]
     */
    val type = HashMap<Class<*>, Int>()

    /**
     * Add the model type and its layout id
     * @param M model type
     * @param id layout Id
     * @see [type]
     */
    inline fun <reified M> addType(@LayoutRes id: Int) {
        type[M::class.java] = id
    }

    /**
     * Get the [mModel] of the current [BindingViewHolder] and convert it to [M] type
     * @param M Required model type
     */
    inline fun <reified M> getModel(): M = mModel as M

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val viewCreate = ViewCreate(viewType)
        onCreate?.invoke(viewCreate)
//        if (viewCreate.view == null) {
        viewCreate.view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
//        }
        return BindingViewHolder(viewCreate)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        mModel = getModel(holder.adapterPosition)
        onBind?.invoke(holder)
    }

    override fun onViewAttachedToWindow(holder: BindingViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached?.invoke(holder)
    }

    override fun onViewDetachedFromWindow(holder: BindingViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetached?.invoke(holder)
    }

    override fun onViewRecycled(holder: BindingViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled?.invoke(holder)
    }

    override fun getItemCount(): Int {
        return (headers?.size ?: 0) + (models?.size ?: 0) + (footers?.size ?: 0)
    }

    override fun getItemViewType(position: Int): Int {
        return type[getModel(position)::class.java] ?: throw NullPointerException(
            "Please call addType for ${getModel(position)::class.java}"
        )
    }

    /**
     * clear all data for [headers],[models],[footers],and execute [notifyDataSetChanged]
     */
    fun clearModels() {
        headers = ArrayList()
        models = ArrayList()
        footers = ArrayList()
        notifyDataSetChanged()
    }

    private fun getModel(position: Int): Any {
        val headerSize = headers?.size ?: 0
        val modelSize = models?.size ?: 0
        return when {
            position < headerSize -> {
                headers?.get(position)
            }

            position >= headerSize && position < modelSize + headerSize -> {
                models?.get(position - headerSize)
            }

            else -> {
                footers?.get(position - headerSize - modelSize)
            }
        } ?: throw NullPointerException("list has empty model.")
    }

    inner class BindingViewHolder(viewCreate: ViewCreate) :
        RecyclerView.ViewHolder(viewCreate.view) {

        internal var onAttached: (BindingViewHolder.() -> Unit)? = null

        internal var onDetached: (BindingViewHolder.() -> Unit)? = null

        internal var onRecycled: (BindingViewHolder.() -> Unit)? = null

        /**
         * [block] will be called when [onViewAttachedToWindow]
         */
        fun onAttached(block: BindingViewHolder.() -> Unit) {
            onAttached = block
        }

        /**
         * [block] will be called when [onViewDetachedFromWindow]
         */
        fun onDetached(block: BindingViewHolder.() -> Unit) {
            onDetached = block
        }

        /**
         * [block] will be called when [onViewRecycled]
         */
        fun onRecycled(block: BindingViewHolder.() -> Unit) {
            onRecycled = block
        }

        /**
         * returns the size of the [headers]
         */
        val headerSize get() = headers?.size ?: 0

        /**
         * returns the size of the [models]
         */
        val modelSize get() = this@VaryAdapter.models?.size ?: 0

        /**
         * returns the size of the [footers]
         */
        val footerSize get() = footers?.size ?: 0

        /**
         * You can use [models] to get the current list.
         */
        val models: List<Any>?
            get() = if (layoutPosition < headerSize) {
                headers
            } else
                if (layoutPosition >= headerSize && layoutPosition < modelSize + headerSize) {
                    this@VaryAdapter.models
                } else {
                    footers
                }

        /**
         * returns the position of the model in the list.
         * @see models
         */
        val modelPosition
            get():Int {
                return if (layoutPosition < headerSize) {
                    layoutPosition
                } else
                    if (layoutPosition >= headerSize && layoutPosition < modelSize + headerSize) {
                        layoutPosition - headerSize
                    } else {
                        (layoutPosition - headerSize - modelSize)
                    }
            }
    }

}
