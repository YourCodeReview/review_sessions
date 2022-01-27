import 'package:flutter/material.dart';
import 'package:role_heroes/utils/builders/error_notification_builder.dart';

class ServerError implements Exception {
  Map _data;

  ServerError({
    @required data,
  }) {
    this._data = data;
  }

  Map get data => this._data;

  Map get errors => this.data['errors'];

  static SnackBar toSnackBar(ServerError error) {
    // TODO move construct to GetIt
    IErrorNotificationBuilder errorNotificationBuilder = ErrorNotificationBuilder();
    errorNotificationBuilder.rest();
    errorNotificationBuilder.build(error);
    return errorNotificationBuilder.getResult();
  }
}
