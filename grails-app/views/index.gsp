<html>
<head>
    <title>Distributed Grails</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css">

    <script src="https://code.angularjs.org/tools/system.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/typescript/1.7.3/typescript.min.js"></script>
    <script src="https://code.angularjs.org/2.0.0-alpha.52/angular2.min.js"></script>
    <script src="https://code.angularjs.org/2.0.0-alpha.52/http.min.js"></script>
    <script>
        System.config({
            transpiler: 'typescript',
            typescriptOptions: { emitDecoratorMetadata: true }
        });
        System.import('./ts/app.ts');
    </script>
</head>
<body>
<dist-grails>Loading...</dist-grails>
</body>
</html>