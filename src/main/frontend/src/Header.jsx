import React, { Component } from 'react';

class Header extends React.Component {
    render() {
        return (
            <React.Fragment><h1> <a href={"/"}>My Blog</a></h1>
            <h6>A simple blog website made in React and Spring.</h6>
                <h5><a href={"http://twitter.com/daily__hedgehog"}><i className={"fab fa-twitter"}></i></a><a href={"http://github.com/jnyste"}><i className="fab fa-github"></i></a><a href={"http://linkedin.com/in/williamhgates"}><i className="fab fa-linkedin-in"></i></a></h5>
                <hr/>
            </React.Fragment>
        );
    }
}

export default Header;