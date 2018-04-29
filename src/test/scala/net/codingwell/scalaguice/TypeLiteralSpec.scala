/*
 *  Copyright 2010-2014 Benjamin Lings
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
package net.codingwell.scalaguice

import org.scalatest.{Matchers, FunSpec}

class TypeLiteralSpec extends FunSpec with Matchers {

  import com.google.inject._

  object OuterObject {
    class Inner
    abstract class SomAbstract
    class SomeImpl extends SomAbstract
  }

  class Outer {
    class Inner
    trait InnerTrait
  }

  describe("type literal creation") {

    it("should create a type literal from a scala wrapper of a java primitive type") {
      typeLiteral[Int] shouldEqual TypeLiteral.get(classOf[Integer])
    }

    it("should create a type literal from a non-generic reference type") {
      typeLiteral[String] shouldEqual TypeLiteral.get(classOf[String])
    }

    it("should create a type from a scala only type") {
      typeLiteral[Any] shouldEqual TypeLiteral.get(classOf[Any])
    }

    it("should create a type from a innerClass type") {
      val outer = new Outer
      typeLiteral[outer.Inner] shouldEqual TypeLiteral.get(classOf[outer.Inner])
    }

    it("should create a type from a inner Trait type") {
      val outer = new Outer
      typeLiteral[outer.InnerTrait] shouldEqual TypeLiteral.get(classOf[outer.InnerTrait])
    }

    it("should create a type from a inner generic Trait type") {
      typeLiteral[Outer.Gen[String]] shouldEqual new TypeLiteral[Outer.Gen[String]]{}
    }

    it("should create a type literal from an Object") {
      typeLiteral[OuterObject.SomAbstract] shouldEqual TypeLiteral.get(classOf[OuterObject.SomAbstract])
    }

    it("should create a type literal from a abstract type") {
      typeLiteral[OuterObject.SomAbstract] shouldEqual TypeLiteral.get(classOf[OuterObject.SomAbstract])
    }

    it("should create a type literal from a generic reference type") {
      typeLiteral[List[String]] shouldEqual new TypeLiteral[List[String]] {}
    }

    it("should convert type parameters to wrapper classes") {
      typeLiteral[List[Int]] shouldEqual new TypeLiteral[List[java.lang.Integer]] {}
    }

    it("should handle nested types") {
      typeLiteral[OuterObject.Inner] shouldEqual TypeLiteral.get(classOf[OuterObject.Inner])
    }

    it("should handle type parameters that are nested types") {
      typeLiteral[List[OuterObject.Inner]] shouldEqual new TypeLiteral[List[OuterObject.Inner]] {}
    }

    it("should handle type parameters that are arrays") {
      typeLiteral[Array[Int]] shouldEqual new TypeLiteral[Array[java.lang.Integer]] {}
    }

    it("should create a type literal from a trait") {
      typeLiteral[A] shouldEqual new TypeLiteral[A] {}
    }

    it("should create a type literal from a sealed trait") {
      typeLiteral[Option[String]] shouldEqual new TypeLiteral[Option[String]] {}
    }

    it("should handle wildcards") {
      typeLiteral[List[_]] shouldEqual new TypeLiteral[List[_]] {}
    }

    it("should handle embedded wildcards with constraints") {
      typeLiteral[Option[_ <: Throwable]] shouldEqual new TypeLiteral[Option[_ <: Throwable]] {}
    }

  }
}
