<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script
      type="text/javascript"
      src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"
    ></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link
      href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
      rel="stylesheet"
      id="bootstrap-css"
    />
    <link rel="stylesheet" href="css/todoList.css" />
    <title>Title</title>
  </head>
  <body>
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <div class="todolist not-done">
            <h1>TODO LIST</h1>
            <input
              id="content"
              type="text"
              class="form-control add-todo"
              placeholder="할일을 입력하고 엔터를 치세요"
              autofocus
            />
            <hr />
            <ul id="sortable" class="list-unstyled">
              <li class="ui-state-default">
                <div class="checkbox">
                  <label>
                    <input onchange="" type="checkbox" value="" />
                    <span>밥먹기</span>
                  </label>
                </div>
              </li>
            </ul>
            <div class="todo-footer">
              <strong>
                <span id="countTodos" class="count-todos">1</span>
              </strong>
              항목 남았음
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="todolist">
            <h1>Already DONE</h1>
            <ul id="done-items" class="list-unstyled">
              <li>
                <div class="checkbox">
                  <label>
                    <input
                      onchange=""
                      class="remove-item"
                      type="checkbox"
                      value=""
                    />
                    <span>잠자기</span>
                  </label>
                  <button
                    onclick=""
                    class="remove-item btn btn-default btn-xs pull-right"
                  >
                    <span class="glyphicon glyphicon-remove"></span>
                  </button>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </body>
  <script>
    console.log("스타일 참고", "https://bootsnipp.com/snippets/QbN51");

    const init = () => {

      $.ajax({
        url: "/api/v2/todo",
        type: "GET"
      })
        .done((result) => {
          console.log(result);
          // 안한 할 일 / 안한 할 일 개수 / 이미 한 일
          const todoList = result.data.todoList.filter((todo) => todo.doneYn == "N");
          const todoCount = todoList.length;
          const doneList = result.data.todoList.filter((todo) => todo.doneYn == "Y");

          $("#sortable").empty();

          for (const todo of todoList) {
            $("#sortable").append(
              `
              <li class="ui-state-default">
                <div class="checkbox">
                  <label>
                    <input onchange="setDone(` + todo.idx + `)" type="checkbox" value="" />
                    <span>` + todo.content + `</span>
                  </label>
                </div>
              </li>
              `
            );
          }

          $("#countTodos").text(todoCount);

          // doneList를 이용해서 already Done에 내용 추가해보기

          $("#done-items").empty();

          for (const todo of doneList) {
            $("#done-items").append(
              `
              <li>
                <div class="checkbox">
                  <label>
                    <input
                      onchange="setDone(` + todo.idx + `)"
                      class="remove-item"
                      type="checkbox"
                      value=""
                    />
                    <span>` + todo.content + `</span>
                  </label>
                  <button
                    onclick="setDelete(` + todo.idx + `)"
                    class="remove-item btn btn-default btn-xs pull-right"
                  >
                    <span class="glyphicon glyphicon-remove"></span>
                  </button>
                </div>
              </li>
              `
            );            
          }
        })
        .fail((error) => {
          alert("에러가 발생했습니다.");
        });

    }

    init();


    // console.log(document.getElementById("content"));
    // console.log(document.querySelector("#content"));
    // $() -> jquery 객체를 리턴한다.
    $("#content").on("keyup", (e) => {
      if (e.keyCode === 13) {
        if ($("#content").val() == "") {
          alert("내용을 입력해 주세요.");
          $("#content").focus();
          return;
        }

        const data = {
          content: $("#content").val(),
        };

        $.ajax({
          url: "/api/v2/todo",
          type: "POST",
          contentType: "application/json;charset=utf-8",
          data: JSON.stringify(data),
        })
          .done((result) => {
            // console.log(result);
            init();
            $("#content").val("");
          })
          .fail((error) => {
            alert("에러가 발생했습니다.");
          });
      }
    });

    const setDone = (idx) => {

      $.ajax({
        url: "/api/v2/todo/" + idx,
        type: "PUT"
      })
        .done((result) => {
          init();
        })
        .fail((error) => {
          alert("에러가 발생했습니다.");
        });

    }

    const setDelete = (idx) => {

      $.ajax({
        url: "/api/v2/todo/" + idx,
        type: "DELETE"
      })
        .done((result) => {
          init();
        })
        .fail((error) => {
          alert("에러가 발생했습니다.");
        });

      }


  </script>
</html>
