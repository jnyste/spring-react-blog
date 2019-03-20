import React, { Component } from 'react';
import './css/App.css';

class Post extends Component {
    render() {
        return (
            <div className={"post"}>
                <h2><a href={"/posts/" + this.props.id}>{this.props.title}</a></h2>
                <p><i>Posted by PLACEHOLDER at {new Date(Date.parse(this.props.createdTime)).toString()}</i></p>
                <p>{this.renderContent(this.props.content)}</p>
                {this.maybeRenderReadMore(this.props.content, this.props.id)}
                <hr></hr>
            </div>
        );
    }

    renderContent(content) {
        if (content.length < 200) {
            return content;
        }
        else {
            return content.substr(0, 200) + "...";
        }
    }

    maybeRenderReadMore(content, id) {
        if(content.length >= 200) {
            return <em><a href={"/posts/" + id}>Read More..</a></em>
        } else {
        }
    }
}

export default Post;
