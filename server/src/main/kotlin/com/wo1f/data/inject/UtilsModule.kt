/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.data.inject

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val utilsModule = module {
    single { Dispatchers.IO }
}
