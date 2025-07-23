import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import { ActiveFormOption } from '../../util/ActiveFormOption'
import subredditService from '../../services/subreddits'
import userService from '../../services/users'
import SettingsCategory from '../Settings/SettingsCategory'
import SettingsField from '../Settings/SettingsField'
import DeleteSubredditForm from './DeleteSubredditForm'
import AddModeratorForm from './AddModeratorForm'
import RemoveModeratorForm from './RemoveModeratorForm'

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
            
        } catch (error) {
            console.log(error)
        }
    }

    const removeModerator = async (username) => {
        try {
            
        } catch (error) {
            console.log(error)
        }
    }

    const deleteSubreddit = async () => {
        try {
            await subredditService.deleteSubreddit(-1, user.id)

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

    return (
        <div className='w-1/2 mt-10 mx-auto'>
            <h1 className='page-header'>
                Moderator Panel
            </h1>

            <div>
                {activeForm !== null && <button className='background-btn background-blur' onClick={closeActiveForm}></button>}
                <div className={`${activeForm === ActiveFormOption.ADD_MODERATOR ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <AddModeratorForm addModerator={addModerator} handleClose={closeActiveForm} />
                </div>
                <div className={`${activeForm === ActiveFormOption.REMOVE_MODERATOR ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <RemoveModeratorForm removeModerator={removeModerator} handleClose={closeActiveForm} />
                </div>
                <div className={`${activeForm === ActiveFormOption.DELETE_SUBREDDIT ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <DeleteSubredditForm deleteSubreddit={deleteSubreddit} handleClose={closeActiveForm} />
                </div>
            </div>

            <div className='mt-10 mx-5'>
                <SettingsCategory name='User management'>
                    <SettingsField name={ActiveFormOption.ADD_MODERATOR} handleClick={() => openActiveForm(ActiveFormOption.ADD_MODERATOR)} />
                    <SettingsField name={ActiveFormOption.REMOVE_MODERATOR} handleClick={() => openActiveForm(ActiveFormOption.REMOVE_MODERATOR)} />
                </SettingsCategory>

                <SettingsCategory name='Subreddit mangement'>
                    <SettingsField name={ActiveFormOption.DELETE_SUBREDDIT} handleClick={() => openActiveForm(ActiveFormOption.DELETE_SUBREDDIT)} />
                </SettingsCategory>
            </div>
        </div>
    )
}

export default ModeratorPage