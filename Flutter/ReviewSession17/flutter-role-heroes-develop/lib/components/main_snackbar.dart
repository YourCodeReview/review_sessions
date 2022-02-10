import 'package:flutter/material.dart';

class MainSnackBar extends SnackBar {
  MainSnackBar({
    Key key,
    void Function() onVisible,
    Widget content,
    Duration duration,
    SnackBarBehavior behavior
  }) : super(
    key: key,
    content: content,
    duration: (duration == null) ? Duration(seconds: 15) : duration,
    padding: EdgeInsets.all(10.0),
    behavior: behavior == null ? SnackBarBehavior.floating : behavior,
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(10.0),
    ),
    // TODO с этим методом есть проблемы. Доразобраться.
    onVisible: onVisible,
  );
}
