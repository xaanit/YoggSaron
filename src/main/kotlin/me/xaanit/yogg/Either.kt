package me.xaanit.yogg

interface Either {
    companion object {
        fun <T> from(constructor: () -> T): Either =
                try {
                    Left(constructor())
                } catch (ex: Throwable) {
                    Right(ex)
                }
    }

}