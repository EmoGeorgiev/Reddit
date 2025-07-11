import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'

const UserComments = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(true)

    if (isEmpty) {
        return (
            <>
                <EmptyContent text={`u/${profile.username} hasn't commented yet`} />
            </>
        )
    }


    return (
        <div>
            
        </div>
    )
}

export default UserComments