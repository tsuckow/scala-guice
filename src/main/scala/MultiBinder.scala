/*
 *  Copyright 2012 Benjamin Lings
 *  Author: Thomas Suckow
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
package uk.me.lings.scalaguice

import com.google.inject._
import com.google.inject.multibindings._

import java.lang.annotation.Annotation
import java.util.{Set => JSet}

import scala.collection.{ immutable => im }

object ScalaMultibinder {
  /**
   * Returns a new multibinder that collects instances of {@code type} in a {@link Set} that is
   * itself bound with no binding annotation.
   */
  def newSetBinder[T : Manifest]( binder:Binder, settype:TypeLiteral[T] ) = {
    val mybinder = binder.skipSources( classOf[ScalaMultibinder[T]] )
    val result = Multibinder.newSetBinder( mybinder, settype )
    binder.bind( Key.get( typeLiteral[im.Set[T]] ) ).toProvider( new SetProvider[T]( Key.get( typeLiteral[JSet[T]] ) ) )
    new ScalaMultibinder( binder, result )
  }

  def newSetBinder[T : Manifest]( binder:Binder, settype:Class[T] ) = {
    val mybinder = binder.skipSources( classOf[ScalaMultibinder[T]] )
    val result = Multibinder.newSetBinder( mybinder, settype )
    binder.bind( Key.get( typeLiteral[im.Set[T]] ) ).toProvider( new SetProvider[T]( Key.get( typeLiteral[JSet[T]] ) ) )
    new ScalaMultibinder( binder, result )
  }

  def newSetBinder[T : Manifest]( binder:Binder, settype:TypeLiteral[T], annotation:Annotation ) = {
    val mybinder = binder.skipSources( classOf[ScalaMultibinder[T]] )
    val result = Multibinder.newSetBinder( mybinder, settype, annotation )
    binder.bind( Key.get( typeLiteral[im.Set[T]], annotation) ).toProvider( new SetProvider[T]( Key.get( typeLiteral[JSet[T]], annotation ) ) )
    new ScalaMultibinder( binder, result )
  }

  def newSetBinder[T : Manifest]( binder:Binder, settype:Class[T], annotation:Annotation ) = {
    val mybinder = binder.skipSources( classOf[ScalaMultibinder[T]] )
    val result = Multibinder.newSetBinder( mybinder, settype, annotation )
    binder.bind( Key.get( typeLiteral[im.Set[T]], annotation) ).toProvider( new SetProvider[T]( Key.get( typeLiteral[JSet[T]], annotation ) ) )
    new ScalaMultibinder( binder, result )
  }

  def newSetBinder[T : Manifest]( binder:Binder, settype:TypeLiteral[T], annotation:Class[_ <: Annotation] ) = {
    val mybinder = binder.skipSources( classOf[ScalaMultibinder[T]] )
    val result = Multibinder.newSetBinder( mybinder, settype, annotation )
    binder.bind( Key.get( typeLiteral[im.Set[T]], annotation) ).toProvider( new SetProvider[T]( Key.get( typeLiteral[JSet[T]], annotation ) ) )
    new ScalaMultibinder( binder, result )
  }

  def newSetBinder[T : Manifest]( binder:Binder, settype:Class[T], annotation:Class[_ <: Annotation] ) = {
    val mybinder = binder.skipSources( classOf[ScalaMultibinder[T]] )
    val result = Multibinder.newSetBinder( mybinder, settype, annotation )
    binder.bind( Key.get( typeLiteral[im.Set[T]], annotation) ).toProvider( new SetProvider[T]( Key.get( typeLiteral[JSet[T]], annotation ) ) )
    new ScalaMultibinder( binder, result )
  }
}

/**
 * Allows binding of scala  via type parameters. Mix into <code>AbstractModule</code>
 *  (or subclass) to allow using a type parameter instead of
 * <code>classOf[Foo]</code> or <code>new TypeLiteral[Bar[Foo]] {}</code>.
 * 
 * For example, instead of
 * {{{
 * class MyModule extends AbstractModule {
 *   def configure {
 *     bind(classOf[Service]).to(classOf[ServiceImpl]).in(classOf[Singleton])
 *     bind(classOf[CreditCardPaymentService])
 *     bind(new TypeLiteral[Bar[Foo]]{}).to(classOf[FooBarImpl])
 *     bind(classOf[PaymentService]).to(classOf[CreditCardPaymentService])
 *   }
 * }
 * }}}
 * use
 * {{{
 * class MyModule extends AbstractModule with ScalaModule {
 *   def configure {
 *     bind[Service].to[ServiceImpl].in[Singleton]
 *     bind[CreditCardPaymentService]
 *     bind[Bar[Foo]].to[FooBarImpl]
 *     bind[PaymentService].to[CreditCardPaymentService]
 *   }
 * }
 * }}}
 *
 * '''Note''' This syntax allows binding to and from generic types.
 * It doesn't currently allow bindings between wildcard types because the
 * manifests for wildcard types don't provide access to type bounds.
 */
class ScalaMultibinder[T : Manifest]( binder:Binder, multibinder:Multibinder[T] ) {
  def addBinding() = {
    new ScalaModule.ScalaLinkedBindingBuilder[T] {
      val self = multibinder.addBinding
    }
  }

  def get() = {
    multibinder
  }

  def permitDuplicates():Unit = {
    multibinder.permitDuplicates
  }

}
