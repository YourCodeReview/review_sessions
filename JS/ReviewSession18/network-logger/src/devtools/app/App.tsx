import * as React from 'react';
import { sendRequestData, sendResponseData } from 'core/helpers';
import { RulesContextProvider } from 'devtools/store/store';
import { Header } from './header';
import { Rules } from './rules-list/rules';
import { Container } from './styles';

chrome.devtools.network.onRequestFinished.addListener(request => {
  const { postData, url, queryString } = request.request;
  sendRequestData({
    postData: postData ? JSON.parse(postData?.text || '') : undefined,
    query: queryString,
    url
  });
  request.getContent(data => {
    sendResponseData({ data, url });
  });
});

export const App: React.FC = () => (
  <Container col>
    <Header />
    <RulesContextProvider>
      <Rules />
    </RulesContextProvider>
  </Container>
);
