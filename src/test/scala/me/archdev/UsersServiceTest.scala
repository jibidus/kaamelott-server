package me.archdev

import org.scalatest.WordSpec
import org.scalatest.concurrent.ScalaFutures
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.MediaTypes
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import me.archdev.restapi.http.routes.UsersServiceRoute
import me.archdev.restapi.models.UserEntity
import spray.json._
import support.DatabaseTest
import org.scalatest.Matchers
import me.archdev.restapi.http.HttpService

class UsersServiceTest extends WordSpec with Matchers with HttpService with ScalatestRouteTest with DatabaseTest with ScalaFutures {
  "Users service" should {
    "retrieve users list" in {
      Get("/users") ~> usersRoute ~> check {
        responseAs[JsArray] should be(testUsers.toJson)
      }
    }

    "retrieve user by id" in {
      Get("/users/1") ~> usersRoute ~> check {
        responseAs[JsObject] should be(testUsers.head.toJson)
      }
    }

    "update user by id and retrieve it" in {
      val newUsername = "UpdatedUsername"
      val requestEntity = HttpEntity(MediaTypes.`application/json`, JsObject("username" -> JsString(newUsername)).toString())
      Post("/users/1", requestEntity) ~> usersRoute ~> check {
        responseAs[JsObject] should be(testUsers.head.copy(username = newUsername).toJson)
        whenReady(getUserById(1)) { result =>
          result.get.username should be(newUsername)
        }
      }
    }

    "delete user" in {
      Delete("/users/3") ~> usersRoute ~> check {
        response.status should be(NoContent)
        whenReady(getUserById(3)) { result =>
          result should be(None: Option[UserEntity])
        }
      }
    }
  }

}
