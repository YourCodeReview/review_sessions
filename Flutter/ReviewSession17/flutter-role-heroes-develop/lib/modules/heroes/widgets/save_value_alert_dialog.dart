import 'package:flutter/material.dart';
import 'package:role_heroes/components/flushbar.dart';
import 'package:role_heroes/components/main_snackbar.dart';
import 'package:role_heroes/utils/value_types.dart';

class SaveFieldValueAlertDialog extends StatelessWidget {
  final String title;
  final IValueType type;
  dynamic value;

  final Future Function(dynamic) setValue;
  final void Function(dynamic) successSaveValue;

  final _editValueController = TextEditingController(text: '');

  SaveFieldValueAlertDialog({
    Key key,
    @required this.title,
    @required this.value,
    @required this.type,
    @required this.setValue,
    @required this.successSaveValue,
  }) : super(key: key);

  void saveValue(BuildContext context) {
    String newValue = _editValueController.text;
    if (newValue == this.value.toString()) {
      MainFlushbar processFlushbar = MainFlushbar(message: 'Process', showProgressIndicator: true)..show(context);
      this.setValue(this.value)
        .then((value) {
          processFlushbar.dismiss();
          Navigator.pop(context);

          ScaffoldMessenger.of(context).showSnackBar(
            MainSnackBar(
              content: Text('Значение обновлено'),
              duration: Duration(seconds: 2),
            )
          );

          this.value = newValue;
          this.successSaveValue(this.value);
        })
        .catchError((error) {
          processFlushbar.dismiss();

          MainFlushbar(
              message: error.toString(),
              statusColor: Colors.red,
              duration: Duration(seconds: 4)
          )..show(context);
          Navigator.pop(context);
        });
    }
  }

  void cancelEdit(BuildContext context) {
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    _editValueController.text = this.value.toString();

    return AlertDialog(
      title: Text(this.title),
      content: TextField(
        onChanged: (newValue) { this.value = this.type.convertValue(newValue); },
        controller: _editValueController,
        keyboardType: this.type.getInputType(),
        autofocus: true,
        decoration: InputDecoration(
            hintText: 'Fill field',
            filled: true,
            focusedBorder: UnderlineInputBorder(),
            labelStyle: Theme.of(context).textTheme.bodyText2
        ),
      ),
      actions: [
        ElevatedButton(
          onPressed: () { saveValue(context); },
          child: Text('Save'),
        ),
        ElevatedButton(
          // TODO create style
          style: ElevatedButton.styleFrom(
            primary: Colors.grey,
          ),
          onPressed: () { cancelEdit(context); },
          child: Text('Cancel'),
        ),
      ],
    );
  }
}
