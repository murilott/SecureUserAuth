import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import '../src/style/base.css'
import User from './components/User'
import Home from './components/Home'
import Login from './components/Login'
import { Route, Routes } from 'react-router-dom'
import Header from './components/Header'
import Register from './components/Register'
import Profile from './components/Profile'

function App() {

  return (
    <div className='app'>
      <Header />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/user" element={<User />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
    </div>
  )
}

export default App
