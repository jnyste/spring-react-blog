import React, { Component } from 'react';
import './css/App.css';

class Post extends Component {
    render() {
        return (
            <div className={"post"}>
                <h2><a href={"/posts/" + this.props.id}>{this.props.title}</a></h2>
                <p><i>Posted by PLACEHOLDER at {new Date(Date.parse(this.props.createdTime)).toString()}</i></p>
                <p>{this.renderContent(this.props.content)}</p>
                <div className={"like-box row"}>
                    <div className={"like-content two columns"}>
                        <i className={"like-thumb fas fa-thumbs-up"}/><span className={"like-number"}>{this.props.likes.length}</span>
                    </div>
                </div>
                {this.maybeRenderReadMore(this.props.content, this.props.id)}
                {this.renderTags(this.props.tags)}
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
            return <React.Fragment><em><a href={"/posts/" + id}>Read More..</a></em></React.Fragment>
        } else {
        }
    }

    renderTags(tags) {
        return <div className={"post-summary-tags"}>
            {tags.sort((a, b) => a.content.localeCompare(b.content)).map((tag) => <a href={"/posts/tag/" + tag.content}><span className={"post-summary-tag"}>{tag.content}</span></a>)}
        </div>;
    }
}

export default Post;
