import 'package:flutter/material.dart';

import '../constants.dart';

class LightTheme {
  static build(context) {
    return ThemeData(
      scaffoldBackgroundColor: Color(0xEFFFFFFF),
      textTheme: TextTheme(
        bodyText1: TextStyle(color: gTextColor, fontSize: 16.0),
        bodyText2: TextStyle(color: gTextColor, fontSize: 18.0),

        headline1: TextStyle(color: gTextColor, fontSize: 28.0, fontWeight: FontWeight.bold),
        headline2: TextStyle(color: gTextColor, fontSize: 24.0, fontWeight: FontWeight.bold),
        headline3: TextStyle(color: gTextColor, fontSize: 20.0, fontWeight: FontWeight.bold),
      ),
    );
  }
}
