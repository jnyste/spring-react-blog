import React, { Component } from 'react';
import './css/App.css';
import PostSummary from './PostSummary';

class PostsPage extends Component {

    constructor(props) {
        super(props);
        this.state = { posts: [] };
    }

    componentDidMount() {
        let pageUrl = "http://localhost:8080/api/posts";

        if (this.props.page) {
            pageUrl = "http://localhost:8080/api/posts/page/" + (this.props.page - 1);
        }

        fetch(pageUrl)
            .then(resp => resp.json())
            .then(resp => resp["content"])
            .then(resp => this.setState({posts: resp}))
    }

    render() {
        return (
            <React.Fragment>
                {this.state.posts &&
                 this.state.posts.map(
                     post =>
                         <PostSummary key={post.id} author={post.author} title={post.title} content={post.content} id={post.id} createdTime={post.createdTime}/>)}
            </React.Fragment>
        )
    }
}

export default PostsPage;