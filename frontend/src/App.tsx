import React, {useEffect} from 'react';
import logo from './logo.svg';
import './App.scss';
import {Navigate, redirect, useNavigate} from "react-router-dom";
import {useAppSelector} from "./hooks";

export function App() {

  // TODO: Cookie?

  const auth = useAppSelector((state) => state.auth)

  if (auth.authorized) {
    return <Navigate to="/main_page"/>
  } else {
    return <Navigate to="/sign_in"/>
  }
}
