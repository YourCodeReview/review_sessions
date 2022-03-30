import * as React from 'react';
import { uuid } from 'common/uuid';
import { ConfigStorage } from 'core/config-storage';
import { Rule, RuleType } from 'devtools/app/rules-list/types';

export interface RulesContext {
  rules: Rule[];
  insertRule: (index: number) => void;
  removeRule: (index: number) => void;
  setRuleType: (index: number, type: RuleType) => void;
  setRuleUrl: (index: number, url: Rule['url']) => void;
  setRulePath: (index: number, path: Rule['path']) => void;
}

const { Provider, Consumer } = React.createContext<RulesContext>({
  rules: [],
  insertRule: () => {},
  removeRule: () => {},
  setRuleType: () => {},
  setRuleUrl: () => {},
  setRulePath: () => {},
});


interface State {
  rules: Rule[];
}

export { Consumer as RulesContextConsumer };

export class RulesContextProvider extends React.Component<unknown, State> {
  state = { rules: [] as Rule[] };

  private insertRule = (index: number) => {
    const rule = makeRule();
    const rules = [...this.state.rules];
    rules.splice(index + 1, 0, rule);
    this.setRules(rules);
  };

  private removeRule = (index: number) => {
    const rules = [...this.state.rules.slice(0, index), ...this.state.rules.slice(index + 1)];
    this.setRules(rules);
  };

  private setRuleType = (index: number, type: RuleType) => {
    const rules = [...this.state.rules];
    const rule = rules[index];
    rules[index] = { ...rule, type: type };
    this.setRules(rules);
  };

  private setRuleUrl = (index: number, url: Rule['url']) => {
    const rules = [...this.state.rules];
    const rule = rules[index];
    rules[index] = { ...rule, url };
    this.setRules(rules);
  };

  private setRulePath = (index: number, path: Rule['path']) => {
    const rules = [...this.state.rules];
    const rule = rules[index];
    rules[index] = { ...rule, path };
    this.setRules(rules);
  };

  private setRules = (rules: Rule[]) => {
    this.setState({ rules });
    ConfigStorage.setRules(rules);
  };

  async componentDidMount() {
    const rules = await ConfigStorage.getRules();
    this.setState({ rules });
  }

  render() {
    const value = {
      rules: this.state.rules,
      insertRule: this.insertRule,
      removeRule: this.removeRule,
      setRuleType: this.setRuleType,
      setRuleUrl: this.setRuleUrl,
      setRulePath: this.setRulePath,
    };

    return (
      <Provider value={value}>{this.props.children}</Provider>
    );
  }
}

function makeRule(): Rule {
  return {
    id: uuid(),
    type: RuleType.Response,
    url: '',
    path: '',
  };
}
