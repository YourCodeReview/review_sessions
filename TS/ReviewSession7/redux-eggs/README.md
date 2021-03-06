Если кратко, то это набор библиотек, который предоставляет следующие возможности:
* разделить ваш огромный redux-store на более мелкие составные части и подключать редьюсеры, мидлвары (саги в том числе) именно там, где вам нужно, чтобы улучшить код-сплиттинг,
* динамически подтягивать части стора только там, где это нужно,
* удалять ненужные куски стора, когда они больше не нужны,
* без проблем самостоятельно расширять возможности библиотеки через механизм событий и расширений.

Проект представляет собой монорепу, которая включает в себя несколько воркспейсов:
* packages - код библиотек, которые собираются и отправляются в NPM,
* examples - код с примерами (пока только на Next.js),
* internal - код, для внутренних нужд монорепозитория, пока там только кастомная версия плагина для Rollup https://www.npmjs.com/package/rollup-plugin-filesize.

Состав packages:
* packages/core - ядро библиотеки, включает в себя всю логику, которая не завязана на конкретный фреймворк/библиотеку (кроме самого Redux),
* packages/next - обёртка для Next.js которая предоставляет возможность работать со стором, API библиотеки напоминает https://github.com/kirill-konshin/next-redux-wrapper,
* packages/react - обёртка для React-компонентов, в которые необходимо динамически подключать куски стора (например, через React.lazy или next/dynamic), имеет небольшую оптимизацию для поддержки React Strict Mode,
* packages/redux - обёртка ядра библиотеки для Redux,
* packages/redux-toolkit - обёртка ядра библиотеки для Redux Toolkit,
* packages/saga-extension - расширение, которое позволяет динамически добавлять в стор и удалять из стора саги.

Требования:
* без сторонних зависимостей для production-режима,
* простой API,
* без необходимости выводить сложные типы пользователям TS,
* легковесный код,
* производительный код,
* высокое покрытие тестами (сейчас всё в packages покрыто тестами на 100%).

Что я хочу от ревью:
* познакомить сообщество со своим проектом,
* получить ревью на packages/core и packages/next,
* получить ваше общее впечатление от кода и проекта,
* получить новые идеи/feature-реквесты.