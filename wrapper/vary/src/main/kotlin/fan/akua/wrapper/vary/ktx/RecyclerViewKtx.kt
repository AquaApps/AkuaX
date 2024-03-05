package fan.akua.wrapper.vary.ktx

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import fan.akua.wrapper.vary.VaryAdapter

fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
): RecyclerView {
    layoutManager = LinearLayoutManager(context).also {
        it.orientation = orientation
    }
    return this
}

fun RecyclerView.staggered(
    spanCount: Int = 1,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
): RecyclerView {
    layoutManager = StaggeredGridLayoutManager(spanCount, orientation)
    return this
}

fun RecyclerView.setup(block: VaryAdapter.(RecyclerView) -> Unit): VaryAdapter {
    val adapter = VaryAdapter()
    adapter.block(this)
    this.adapter = adapter
    return adapter
}


/**
 * add model to [RecyclerAdapter.headers]
 * @param model model to be added
 */
fun RecyclerView.addHeader(model: Any) {
    recyclerAdapter.run {
        addHeader(model, headers?.size ?: 0)
    }
}

/**
 * add model to [RecyclerAdapter.headers]
 * @param model model to be added
 * @param position inserted position
 */
fun RecyclerView.addHeader(model: Any, position: Int) {
    recyclerAdapter.run {
        if (headers != null) {
            val list = headers?.toMutableList() ?: ArrayList()
            list.add(position, model)
            headers = list
            return
        }
        headers = listOf(model)
    }
}

/**
 * add model to [RecyclerAdapter.models]
 * @param models model to be added
 */
fun RecyclerView.addModels(models: List<Any>) {
    recyclerAdapter.run {
        addModels(models, this.models?.size ?: 0)
    }
}

/**
 * add model to [RecyclerAdapter.models]
 * @param models models to be added
 * @param position inserted position
 */
fun RecyclerView.addModels(models: List<Any>, position: Int) {
    recyclerAdapter.run {
        if (this.models != null) {
            val list = this.models?.toMutableList() ?: ArrayList()
            list.addAll(position, models)
            this.models = list
            return
        }
        this.models = models
    }
}

/**
 * add model to [RecyclerAdapter.footers]
 * @param model model to be added
 */
fun RecyclerView.addFooter(model: Any) {
    recyclerAdapter.run {
        addFooter(model, footers?.size ?: 0)
    }
}

/**
 * add model to [RecyclerAdapter.footers]
 * @param model model to be added
 * @param position inserted position
 */
fun RecyclerView.addFooter(model: Any, position: Int) {
    recyclerAdapter.run {
        if (footers != null) {
            val list = footers?.toMutableList() ?: ArrayList()
            list.add(position, model)
            footers = list
            return
        }
        footers = listOf(model)
    }
}

inline val RecyclerView.recyclerAdapter
    get() = (adapter as VaryAdapter)
