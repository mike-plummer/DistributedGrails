<html>
<head>
    <title>Distributed Grails</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css">

    <script src="https://code.angularjs.org/tools/system.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/typescript/1.7.3/typescript.min.js"></script>

    <script src="https://code.angularjs.org/2.0.0-beta.0/angular2-polyfills.min.js"></script>
    <script src="https://code.angularjs.org/2.0.0-beta.0/Rx.min.js"></script>
    <script src="https://code.angularjs.org/2.0.0-beta.0/angular2.min.js"></script>
    <script src="https://code.angularjs.org/2.0.0-beta.0/http.min.js"></script>

    <script>
        System.config({
            transpiler: 'typescript',
            typescriptOptions: { emitDecoratorMetadata: true }
        });
        System.import('angular2/platform/browser').then(function(ng) {
            System.import('./ts/app.ts').then(function (src) {
                ng.bootstrap(src.AppComponent);
            });
        });
    </script>
</head>
<body>
<dist-grails>Loading...</dist-grails>
</body>
</html>