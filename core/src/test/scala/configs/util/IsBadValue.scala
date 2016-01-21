/*
 * Copyright 2013-2016 Tsukasa Kitachi
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

package configs.util

import com.typesafe.config.ConfigException
import configs.Result
import scala.util.{Failure, Try}
import scalaz.Need

trait IsBadValue[A] {
  def check(a: Need[Result[A]]): Boolean
}

object IsBadValue {

  def apply[A](implicit A: IsBadValue[A]): IsBadValue[A] = A


  implicit def defaultIsBadValue[A]: IsBadValue[A] =
    default.asInstanceOf[IsBadValue[A]]

  private[this] final val default: IsBadValue[Any] =
    _.value.fold(_.toConfigException.isInstanceOf[ConfigException.BadValue], _ => false)


  implicit def eitherIsBadValue[A]: IsBadValue[Either[Throwable, A]] =
    either.asInstanceOf[IsBadValue[Either[Throwable, A]]]

  private[this] final val either: IsBadValue[Either[Throwable, Any]] =
    _.value.exists(_.left.exists(_.isInstanceOf[ConfigException.BadValue]))


  implicit def tryIsBadValue[A]: IsBadValue[Try[A]] =
    tryB.asInstanceOf[IsBadValue[Try[A]]]

  private[this] final val tryB: IsBadValue[Try[Any]] =
    _.value.exists {
      case Failure(_: ConfigException.BadValue) => true
      case _                                    => false
    }

}