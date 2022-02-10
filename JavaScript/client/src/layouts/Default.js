import React from 'react'
import { useRecoilState } from "recoil"
import { Link } from "react-router-dom"

import SwitcherMode from '../components/SwitcherMode'

import userState from "../atoms/userAtoms"

const Default = ({children}) => {
    const [user] = useRecoilState(userState)
    console.log(user);

    return (<>
        <nav className="bg-blue-800 navbar-dark navbar navbar-fixed-top">
            <div className="navbar-container-fluid">
                <div className="navbar-content-menu">
                    <ul className="navbar-menu navbar-menu-left">
                        <li className="navbar-item">
                            <Link className="navbar-link" to="/">Home</Link>
                        </li>
                        
                        { user.isAuth && <li className="navbar-item">
                            <Link className="navbar-link" to ='/moviesWatched'>Movies Watched</Link>
                        </li>}
                    </ul>

                    <ul className="navbar-menu-right plr-10">
                        { !user.isAuth && <li className="navbar-item"><Link className="navbar-link" to ='/login'>Login</Link></li> }
                        { !user.isAuth && <li className="navbar-item"><Link className="navbar-link" to='/register'>Register</Link></li> }
                        { user.isAuth && <li className="navbar-item"><Link className="navbar-link" to ='/account'>{user.username}</Link></li>}
                        { user.isAuth && <li className="navbar-item"><Link className="navbar-link" to ='/logout'>Logout</Link></li>}
                        <li className="navbar-item"><SwitcherMode /></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div className="container ptb-40">
            {children}
        </div>
    </>)
}

export default Default
