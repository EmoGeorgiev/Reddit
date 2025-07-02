import SettingsCategory from './SettingsCategory'
import SettingsField from './SettingsField'

const Settings = () => {
    return (
        <div className='w-1/2 mt-10  mx-auto'>
            <h1 className='text-gray-800 text-3xl text-center font-bold'>Settings</h1>
            
            <div className='mt-10 mx-5'>
                <SettingsCategory name='General'>
                    <SettingsField name='Username' />
                    <SettingsField name='Password' />
                </SettingsCategory>
                        
                <SettingsCategory name='Advanced'>
                    <SettingsField name='Delete account' />
                </SettingsCategory>
            </div>
        </div>
    )
}

export default Settings