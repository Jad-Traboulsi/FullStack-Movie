import React,{useContext} from 'react'
import {FaMoon,FaSun} from 'react-icons/fa'
import { ThemeContext } from '../contexts/themes'
const SwitcherMode = () => {
    const [{theme,isDark},toggleTheme] = useContext(ThemeContext)
    return (
      <div>
        {!isDark ? <FaSun onClick={toggleTheme} /> : <FaMoon onClick={toggleTheme} />}
      </div>
    );
}

export default SwitcherMode
