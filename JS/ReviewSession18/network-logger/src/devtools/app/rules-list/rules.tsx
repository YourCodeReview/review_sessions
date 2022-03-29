import * as React from 'react';
import { Border } from 'devtools/react-controls/border';
import { Box } from 'devtools/react-controls/box';
import { IconButton } from 'devtools/react-controls/icon-button';
import { TextInput } from 'devtools/react-controls/text-input';
import { RulesContext, RulesContextConsumer } from 'devtools/store/store';
import { Color } from 'devtools/styles/status-colors';
import { Rule, RuleType } from './types';

export const Rules: React.FC = () => (
  <RulesContextConsumer>
    {context => (
      <React.Fragment>
        <Border top />
        <Box col spacing grow style={{ width: '80%' }} padding='v' overflow marginLeft marginRight>
          {context.rules.map((rule, i) => (
            <RuleItem key={rule.id} index={i} rule={rule} context={context} />
          ))}
        </Box>
      </React.Fragment>
    )}
  </RulesContextConsumer>
);


interface RuleItemProps {
  index: number;
  rule: Omit<Rule, 'id'>;
  context: RulesContext;
}

const RuleItem: React.FC<RuleItemProps> = (props) => {
  const { rule, index, context } = props;

  const isRequest = () => rule.type == RuleType.Request;

  const onTypeToggle = () => context.setRuleType(
    index,
    isRequest() ? RuleType.Response : RuleType.Request
  );

  const onPathChange = (path: string) => context.setRulePath(index, path);

  const onUrlChange = (url: string) => context.setRuleUrl(index, url);

  const onRemove = () => context.removeRule(index);

  const onAdd = () => context.insertRule(index);

  const disabled = context.rules.length == 1;

  const icon = {
    delete: getActionIcon('delete'),
    add: getActionIcon('new'),
    type: getActionIcon(isRequest() ? 'data-in' : 'data-out'),
  };

  const typeTitle = `Switch to ${isRequest() ? 'response' : 'request'} mode`;

  return (
    <Box row spacing padding='h'>
      <Box col spacingSm>
        <IconButton src={icon.type} onClick={onTypeToggle} title={typeTitle} />
      </Box>
      <Box col spacingSm grow>
        <TextInput placeholder='/api/request/method' value={rule.url} onChange={onUrlChange} />
        <TextInput placeholder='path.to.item[0].name' value={rule.path} onChange={onPathChange} />
      </Box>
      <Box col spacingSm>
        <IconButton src={icon.delete} onClick={onRemove} disabled={disabled} status={Color.error} />
        <IconButton src={icon.add} onClick={onAdd} />
      </Box>
    </Box>
  );
};

function getActionIcon<T extends string>(icon: T) {
  return `/assets/icons/actions/${icon}.svg` as const;
}
