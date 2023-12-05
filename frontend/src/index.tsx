import './index.scss';
import 'react-date-picker/dist/DatePicker.css';
import 'react-calendar/dist/Calendar.css';

import React from 'react';
import ReactDOM from 'react-dom/client';
import {
    createBrowserRouter,
    RouterProvider
} from "react-router-dom";
import {App} from './App';
import {NotFound} from "./pages/NotFound/NotFound";
import {SignIn} from "./pages/SignIn/SignIn";
import {SignUp} from "./pages/SignUp/SignUp";
import {MainPage} from "./pages/MainPage/MainPage";
import {OrdersList} from "./pages/OrdersList/OrdersList";
import {WarehousesList} from "./pages/WarehousesList/WarehousesList";
import {EmployeesList} from "./pages/EmployеesList/EmployеesList";
import {ClientsList} from "./pages/ClientsList/ClientsList";
import {BranchesList} from "./pages/BranchesList/BranchesList";
import {NewWarehouse} from "./pages/NewWarehouse/NewWarehouse";
import {NewBranch} from "./pages/NewBranch/NewBranch";
import {NewEmployee} from "./pages/NewEmployee/NewEmployee";
import {NewClient} from "./pages/NewClient/NewClient";
import {NewOrder} from "./pages/NewOrder/NewOrder";

import {Provider} from "react-redux";
import store from "./store";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
        errorElement: <NotFound/>
    },
    {
        path: "/sign_in",
        element: <SignIn/>
    },
    {
        path: "/sign_up",
        element: <SignUp/>
    },
    {
        path: "/main_page",
        element: <MainPage/>
    },
    {
        path: "/orders-list",
        element: <OrdersList/>
    },
    {
        path: "/warehouses-list",
        element: <WarehousesList/>
    },
    {
        path: "/employees-list",
        element: <EmployeesList/>
    },
    {
        path: "/clients-list",
        element: <ClientsList/>
    },
    {
        path: "/branches-list",
        element: <BranchesList/>
    },
    {
        path: "/not-found",
        element: <NotFound/>
    },
    {
        path: "/new-warehouse",
        element: <NewWarehouse/>
    },
    {
        path: "/new-branch",
        element: <NewBranch/>
    },
    {
        path: "/new-employee",
        element: <NewEmployee/>
    },
    {
        path: "/new-client",
        element: <NewClient/>
    },
    {
        path: "/new-order",
        element: <NewOrder/>
    }
]);

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <RouterProvider router={router}/>
        </Provider>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
