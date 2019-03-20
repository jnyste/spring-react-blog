import React, { Component } from 'react';

class PageListing extends Component {
    render() {
        return (
            <div className={"u-pull-right"}>
                {[1, 2, 3, 4, 5].map((num) => <a key={num} href={"/page/" + num}><button>{num}</button></a>)}
            </div>
        );
    }
}

export default PageListing;
