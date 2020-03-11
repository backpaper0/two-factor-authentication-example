<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>2要素認証 - 2要素認証example</title>
  </head>
  <body>
    <h1>2要素認証</h1>
    <form method="POST">
      <p><input type="text" name="otp" placeholder="ワンタイムパスワード"></p>
      <p><button type="submit">2要素認証する</button></p>
    </form>
  </body>
</html>
