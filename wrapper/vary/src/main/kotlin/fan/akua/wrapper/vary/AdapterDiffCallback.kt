package fan.akua.wrapper.vary

import androidx.recyclerview.widget.DiffUtil


internal class AdapterDiffCallback(private val mOldList: List<Any>, private val mNewList: MutableList<Any>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].javaClass == mNewList[newItemPosition].javaClass
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldObj = mOldList[oldItemPosition]
        val newObj = mNewList[newItemPosition]
        return oldObj == newObj
    }
}