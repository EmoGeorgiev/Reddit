import { BrowserRouter as Router } from 'react-router-dom'
import AuthProvider from './components/Authentication/AuthProvider'
import Layout from './components/Layout'

const App = () => {
  return(
    <Router>
      <AuthProvider>
        <Layout />
      </AuthProvider>
    </Router>
  )
}

export default App