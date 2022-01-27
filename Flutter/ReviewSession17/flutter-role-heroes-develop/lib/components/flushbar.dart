import 'package:flushbar/flushbar.dart';
import 'package:flutter/material.dart';

// TODO збавится от него
class MainFlushbar extends Flushbar
{
  MainFlushbar({
    @required message,
    bool showProgressIndicator = false,
    Color statusColor,
    Duration duration,
  }) : super(
    message: message,
    showProgressIndicator: showProgressIndicator,
    margin: EdgeInsets.all(8),
    leftBarIndicatorColor: statusColor,
    duration: duration,
  );
}
