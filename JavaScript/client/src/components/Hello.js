import React from 'react'

const Hello = ({name}) => {
    const fileName = name || "There! I hope you enjoy it here"
    return (
        <h1 className="title">Hello {fileName}!</h1>
    )
}

export default Hello
