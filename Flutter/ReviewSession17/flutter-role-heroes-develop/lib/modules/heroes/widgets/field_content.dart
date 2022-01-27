import 'package:flutter/material.dart';

class FieldContent extends StatelessWidget {
  final String name;
  final String value;
  final bool isLargeContent;

  const FieldContent({
    Key key,
    @required this.name,
    @required this.value,
    @required this.isLargeContent,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final List<Widget> content = [
      Text(this.name, style: Theme.of(context).textTheme.headline3),
      Text(this.value, style: Theme.of(context).textTheme.bodyText1)
    ];

    if (this.isLargeContent) {
      return Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: content,
      );
    } else {
      return Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: content,
      );
    }
  }
}
