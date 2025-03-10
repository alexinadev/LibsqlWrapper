package tech.turso.libsql

import tech.turso.libsql.proto.Row as ProtoRow

typealias Row = List<Any?>

class Rows internal constructor(private var inner: Long) : AutoCloseable, Iterable<Row> {
    init {
        android.util.Log.i("mylibsql", "Created with ptr: $inner")
        require(this.inner != 0L) { "Attempted to construct a Rows with a null pointer" }
    }

    fun next(): Row {
        val buf: ByteArray = nativeNext(this.inner)
        return ProtoRow.parseFrom(buf).valuesList.map {
            when {
                it.hasInteger() -> it.integer
                it.hasText() -> it.text
                it.hasReal() -> it.real
                it.hasBlob() -> it.blob.toByteArray()
                it.hasNull() -> null
                else -> RuntimeException("Invalid Row")
            }
        }
    }

    override fun close() {
        android.util.Log.i("mylibsql", "closing ptr: $inner")
        require(this.inner != 0L) { "Rows object already closed" }
        nativeClose(this.inner)
        this.inner = 0
    }

    override fun iterator(): Iterator<Row> = RowsIterator(this)

    private external fun nativeNext(rows: Long): ByteArray

    private external fun nativeClose(rows: Long)
}
