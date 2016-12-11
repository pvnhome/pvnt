PVNT
=========

### Introduction

_PVNT is a simple, open source template utility written in Java._

* Version: 0.90 (beta)
* Author: [pvnhome](mailto:pvnhome@yandex.ru)

### Features

Template file:
```html
<!DOCTYPE html>
<html lang="ru">
<!--pvnTmplBeg-->
<head>
   <meta charset="utf-8">
   <!--pvnEditBeg title-->
   <title>PageTemplate</title>
   <!--pvnEditEnd-->
</head>
<body>
   <h1>Site1 (A,B)</h1>
   <h2>Part1</h2>
   <!--pvnEditBeg part1-->
   <p>Palceholder for Part1</p>
   <!--pvnEditEnd-->
   <h2>Part2</h2>
   <!--pvnEditBeg part2-->
   <p>Palceholder for Part2</p>
   <!--pvnEditEnd-->
</body>
<!--pvnTmplEnd-->
</html>
```

HTML-file A.html:
```html
<!DOCTYPE html>
<html lang="ru">
<!--pvnTmplBeg templates/template_ru.html-->
<head>
   <meta charset="utf-8">
   <!--pvnImplBeg title-->
   <title>PageA</title>
   <!--pvnImplEnd-->
</head>
<body>
   <h1>Site1 (A,B)</h1>
   <h2>Part1</h2>
   <!--pvnImplBeg part1-->
   <p>Content for Part1 on PageA.</p>
   <!--pvnImplEnd-->
   <h2>Part2</h2>
   <!--pvnImplBeg part2-->
   <p>Content for Part2 on PageA.</p>
   <!--pvnImplEnd-->
</body>
<!--pvnTmplEnd-->
</html>
```

### Website
* PVNT Source code
<https://github.com/pvnhome/pvnt>
* Binary downloads 
<http://sourceforge.net/projects/pvnt/files>
