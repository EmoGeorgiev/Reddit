import SubredditHeader from './SubredditHeader'
import SubredditButtonPanel from './SubredditButtonPanel'

const SubredditPanel = ({ title, isMember, isModerator, createPost, leaveSubreddit, joinSubreddit }) => {
    return (
        <div className='mx-4 my-12 py-5 flex justify-between items-center'>
            <SubredditHeader title={title} />
            <SubredditButtonPanel title={title} 
                                isMember={isMember}
                                isModerator={isModerator}
                                createPost={createPost} 
                                leaveSubreddit={leaveSubreddit} 
                                joinSubreddit={joinSubreddit} />
        </div>
    )
}

export default SubredditPanel