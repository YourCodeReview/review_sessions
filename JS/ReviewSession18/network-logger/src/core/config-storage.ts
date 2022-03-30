import { getDefaultRules } from 'core/default-rules';
import { Rule } from 'devtools/app/rules-list/types';

const enum Key {
  Rules = 'rules'
}

export class ConfigStorage {
  static getRules = () => new Promise<Rule[]>((resolve) => {
    chrome.storage.sync.get(Key.Rules, (items) => {
      const rules: string | null = items[Key.Rules];
      resolve(rules ? JSON.parse(rules) : getDefaultRules());
    });
  });

  static setRules = (rules: Rule[]) => new Promise<void>((resolve, reject) => {
    chrome.storage.sync.set({ [Key.Rules]: JSON.stringify(rules) }, () => {
      if (chrome.runtime.lastError)
        reject(chrome.runtime.lastError);
      else
        resolve();
    });
  });
}
