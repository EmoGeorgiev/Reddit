import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'

const UserComments = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(true)
    return (
        <div>
            {isEmpty && <EmptyContent text={`u/${profile.username} hasn't commented yet`} />}
        </div>
    )
}

export default UserComments