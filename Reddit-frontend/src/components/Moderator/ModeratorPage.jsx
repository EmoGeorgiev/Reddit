import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import { ActiveFormOption } from '../../util/ActiveFormOption'
import subredditService from '../../services/subreddits'
import userService from '../../services/users'
import DeleteSubredditForm from './DeleteSubredditForm'
import AddModeratorForm from './AddModeratorForm'
import RemoveModeratorForm from './RemoveModeratorForm'
import OptionPanel from '../Options/OptionPanel'
import OptionFormManager from '../Options/OptionFormManager'

const ModeratorPage = () => {
    const [activeForm, setActiveForm] = useState(null)
    const [moderators, setModerators] = useState([])
    const { name } = useParams()
    const { user } = useAuth()
    const navigate = useNavigate()

    useEffect(() => {
        const getModerators = async () => {
            try {
                const newModerators = await userService.getModeratorsBySubredditTitle(name)
                
                setModerators(newModerators)

                if (newModerators.find(moderator => moderator.id === user?.id) === undefined) {
                    navigate(`/r/${name}`)
                }
            } catch (error) {
                console.log(error)
            }
        }

        getModerators()
    }, [name])

    const addModerator = async (username) => {
        try {
            const moderatorUpdate = {
                'moderatorId': user.id,
                'updatedModeratorUsername': username
            }
            const newModerator = await userService.addSubredditModerator(name, moderatorUpdate)

            setModerators([...moderators, newModerator])

            closeActiveForm()
        } catch (error) {
            console.log(error)
        }
    }

    const removeModerator = async (username) => {
        try {
            const moderatorUpdate = {
                'moderatorId': user.id,
                'updatedModeratorUsername': username
            }

            const removedModerator = await userService.removeSubredditModerator(name, moderatorUpdate)

            setModerators(moderators.filter(moderator => moderator.id !== removedModerator.id))
            
            closeActiveForm()
        } catch (error) {
            console.log(error)
        }
    }

    const deleteSubreddit = async () => {
        try {
            await subredditService.deleteSubreddit(name, user.id)

            navigate('/')
        } catch (error) {
            console.log(error)
        }
    }

    const openActiveForm = (newActiveForm) => {
        setActiveForm(newActiveForm)
    }

    const closeActiveForm = () => {
        setActiveForm(null)
    }

    const forms = [
        {
            formKey: ActiveFormOption.ADD_MODERATOR, 
            element: (<AddModeratorForm addModerator={addModerator} 
                                        handleClose={closeActiveForm} />) 
        },
        {
            formKey: ActiveFormOption.REMOVE_MODERATOR, 
            element: (<RemoveModeratorForm removeModerator={removeModerator} 
                                           handleClose={closeActiveForm} />) 
        },
        {
            formKey: ActiveFormOption.DELETE_SUBREDDIT, 
            element: (<DeleteSubredditForm deleteSubreddit={deleteSubreddit} 
                                           handleClose={closeActiveForm} />) 
        }
    ]

    const userManagementSettings = [
        { label: ActiveFormOption.ADD_MODERATOR, formKey: ActiveFormOption.ADD_MODERATOR },
        { label: ActiveFormOption.REMOVE_MODERATOR, formKey: ActiveFormOption.REMOVE_MODERATOR },
    ]

    const subredditManagementSettings = [
        { label: ActiveFormOption.DELETE_SUBREDDIT, formKey: ActiveFormOption.DELETE_SUBREDDIT },
    ]

    const sections = [
        { title: 'User management', options: userManagementSettings },
        { title: 'Subreddit mangement', options: subredditManagementSettings }
    ]

    return (
        <div className='w-1/2 mt-10 mx-auto'>
            <h1 className='page-header'>
                Moderator Panel
            </h1>

            <OptionFormManager activeForm={activeForm}
                                forms={forms}
                                handleClose={closeActiveForm} />

            <OptionPanel sections={sections} 
                         onSelect={openActiveForm} />
        </div>
    )
}

export default ModeratorPage