<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ログイン - 2要素認証example</title>
  </head>
  <body>
    <h1>ログイン</h1>
    <form method="POST">
      <p><input type="text" name="username" placeholder="ユーザーID" autofocus></p>
      <p><input type="password" name="password" placeholder="パスワード"></p>
      <p><button type="submit">ログインする</button></p>
    </form>
  </body>
</html>
