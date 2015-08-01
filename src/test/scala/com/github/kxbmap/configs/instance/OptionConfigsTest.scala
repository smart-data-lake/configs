/*
 * Copyright 2013-2015 Tsukasa Kitachi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.kxbmap.configs.instance

import com.github.kxbmap.configs.ConfigProp
import com.github.kxbmap.configs.util.{CValue, IsMissing}
import scalaprops.Scalaprops
import scalaz.std.option._

object OptionConfigsTest extends Scalaprops with ConfigProp {

  val option = check[Option[java.time.Duration]]

  implicit def isMissing[T]: IsMissing[Option[T]] =
    _.value.isEmpty

  implicit def optionCValue[T: CValue]: CValue[Option[T]] =
    _.map(CValue[T].toConfigValue).orNull

}
