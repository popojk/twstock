import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import TopNavbar from './components/navbar/TopNavbar';
import Selector from './components/selector/Selector';
import chartCollections from './data/chart-collections.json';
import Index from './components/TaiwanStockTA/Index';
import TAMainPage from './pages/TAMainPage';
import InstitutionStats from './pages/InstitutionStats';
import TA from './pages/TA';
import Home from './pages/Home';
import StockNotify from './pages/StockNotify';
import Register from './pages/Register';
import PrivateRoute from './components/PrivateRoute';
import Login from './pages/Login';
import MarginShort from './pages/MarginShort';

function App() {
  const charts = chartCollections;
  return (
    <div>
      <Router>
        <TopNavbar />
        <Routes>
          <Route path='/user/register' element={<Register />} />
          <Route path='/user/login' element={<Login />} />
          <Route path='/' element={<PrivateRoute />}>
            <Route path='/home' element={<Home />} />
            <Route path='/stock_detail/:id' element={<TAMainPage />} />
            <Route path='/stock_detail/:id/ta' element={<TA />} />
            <Route path='/stock_detail/:id/institutionstats' element={<InstitutionStats />} />
            <Route path='/stock_detail/:id/stocknotify' element={<StockNotify />} />
            <Route path='/stock_detail/:id/marginshort' element={<MarginShort />} />
          </Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
