import ReactDOM from 'react-dom'
import { ThemeProvider } from './contexts/themes'
import App from './App'
import './index.css'
import { RecoilRoot } from 'recoil';

ReactDOM.render(
  <ThemeProvider>
    <RecoilRoot>
      <App />
    </RecoilRoot>
  </ThemeProvider>,

  document.getElementById("root")
);

