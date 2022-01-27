import 'package:flutter/material.dart';

class PreLoader {
  static bool _show = false;

  static show(BuildContext context) {
    if (!PreLoader._show) {
      showDialog(
        context: context,
        barrierDismissible: false,
        builder: (context) {
          return Center(
            child: CircularProgressIndicator(),
          );
        }
      );
      PreLoader._show = true;
    }
  }

  static hide(BuildContext context) {
    if (PreLoader._show) {
      Navigator.of(context).pop();
      PreLoader._show = false;
    }
  }
}
