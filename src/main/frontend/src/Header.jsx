import React, { Component } from 'react';

class Header extends React.Component {
    render() {
        return (
            <React.Fragment><h1> <a href={"/"}>My Blog</a></h1>
            <h6>A simple blog website made in React and Spring.</h6>
                <h5><i className={"fab fa-twitter"}/><i className="fab fa-github"></i><i className="fab fa-linkedin-in"></i></h5>
                <hr/>
            </React.Fragment>
        );
    }
}

export default Header;