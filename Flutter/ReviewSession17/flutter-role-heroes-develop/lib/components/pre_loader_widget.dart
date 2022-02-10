import 'package:flutter/material.dart';

class PreLoaderWidget extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      child: CircularProgressIndicator(),
      width: 60,
      height: 60,
    );
  }
}
