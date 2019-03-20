import React, { Component } from 'react';
import './css/App.css';
import Header from './Header.jsx';
import PageListing from './PageListing.jsx';
import PostsPage from './PostsPage.jsx';

class Index extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {

    }


    render() {
        return (
            <div className={"container"}>
                <Header/>
                <PostsPage page={this.props.match.params.page}/>
                <PageListing/>
            </div>
        );
    }
}

export default Index;
