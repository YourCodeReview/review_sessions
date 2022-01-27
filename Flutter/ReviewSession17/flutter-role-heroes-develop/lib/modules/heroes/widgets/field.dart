import 'package:flutter/material.dart';
import 'package:role_heroes/modules/heroes/widgets/field_content.dart';
import 'package:role_heroes/modules/heroes/widgets/save_value_alert_dialog.dart';
import 'package:role_heroes/utils/value_types.dart';

import '../../../constants.dart';

class Field extends StatefulWidget {
  String name;
  IValueType type;
  dynamic value;
  Future Function(dynamic) setValue;

  Field({
    @required this.name,
    @required this.type,
    @required this.value,
    @required this.setValue,
  });

  @override
  State<StatefulWidget> createState() => _FieldState();
}

class _FieldState extends State<Field> {
  void onSubmitField(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return SaveFieldValueAlertDialog(
          title: widget.name,
          value: widget.value,
          type: widget.type,
          setValue: widget.setValue,
          successSaveValue: (value) {
            setState(() {
              widget.value = value;
            });
          }
        );
      },
    );
  }

  bool _isLargeValue() {
    return widget.value.toString().length > 25;
  }

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () { onSubmitField(context); },
      child: Container(
        margin: const EdgeInsets.symmetric(horizontal: gDefaultMargin, vertical: gDefaultMargin / 2),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(12),
          color: Colors.white,
        ),
        child: Padding(
          padding: const EdgeInsets.all(gDefaultPadding),
          child: FieldContent(
            name: widget.name,
            value: widget.value.toString(),
            isLargeContent: _isLargeValue(),
          ),
        ),
      ),
    );
  }
}
