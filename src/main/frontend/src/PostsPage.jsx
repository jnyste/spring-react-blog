import React, { Component } from 'react';
import './css/App.css';
import PostSummary from './PostSummary';

class PostsPage extends Component {

    constructor(props) {
        super(props);
        this.state = { posts: [] };
    }

    componentDidMount() {
        let pageUrl = "/api/posts";

        if (this.props.page) {
            pageUrl = "/api/posts/page/" + (this.props.page - 1);
        }

        if (this.props.tag) {
            pageUrl = "/api/posts/tag/" + this.props.tag;
        }

        fetch(pageUrl)
                    .then(resp => resp.json())
            .then(resp => { return this.props.tag ? resp : resp["content"] })
            .then(resp => { this.props.tag ? this.setState({posts: resp}) : this.setState({posts: resp})})
    }

    render() {
        return (
            <React.Fragment>
                {this.state.posts &&
                 this.state.posts.map(
                     post =>
                         <PostSummary key={post.id} author={post.author} title={post.title} content={post.content} id={post.id} createdTime={post.createdTime} likes={post.likes} tags={post.tags}/>)}
            </React.Fragment>
        )
    }
}

export default PostsPage;