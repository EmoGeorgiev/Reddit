import { useState } from "react";
import { ActiveFormOption } from "../../util/ActiveFormOption";
import { useAuth } from "../../hooks/useAuth";
import UsernameChangeForm from "./UsernameChangeForm";
import PasswordChangeForm from "./PasswordChangeForm";
import DeleteAccountForm from "./DeleteAccountForm";
import userService from "../../services/users";
import OptionPanel from "../Options/OptionPanel";
import OptionFormManager from "../Options/OptionFormManager";

const Settings = () => {
  const [activeForm, setActiveForm] = useState(null);
  const { user, updateUser, logout } = useAuth();

  const usernameChange = async (username) => {
    try {
      const newUser = await userService.updateUsername(user.id, {
        id: user.id,
        username,
      });

      updateUser(newUser);

      closeActiveForm();
    } catch (error) {
      throw error;
    }
  };

  const passwordChange = async (oldPassword, newPassword) => {
    try {
      await userService.updatePassword(user.id, { oldPassword, newPassword });

      closeActiveForm();
    } catch (error) {
      throw error;
    }
  };

  const deleteAccount = async (password) => {
    try {
      await userService.deleteUser(user.id, password);

      closeActiveForm();

      logout();
    } catch (error) {
      throw error;
    }
  };

  const openActiveForm = (newActiveForm) => {
    setActiveForm(newActiveForm);
  };

  const closeActiveForm = () => {
    setActiveForm(null);
  };

  const forms = [
    {
      formKey: ActiveFormOption.CHANGE_USERNAME,
      element: (
        <UsernameChangeForm
          usernameChange={usernameChange}
          handleClose={closeActiveForm}
        />
      ),
    },
    {
      formKey: ActiveFormOption.CHANGE_PASSWORD,
      element: (
        <PasswordChangeForm
          passwordChange={passwordChange}
          handleClose={closeActiveForm}
        />
      ),
    },
    {
      formKey: ActiveFormOption.DELETE_ACCOUNT,
      element: (
        <DeleteAccountForm
          deleteAccount={deleteAccount}
          handleClose={closeActiveForm}
        />
      ),
    },
  ];

  const generalSettings = [
    {
      label: ActiveFormOption.CHANGE_USERNAME,
      formKey: ActiveFormOption.CHANGE_USERNAME,
    },
    {
      label: ActiveFormOption.CHANGE_PASSWORD,
      formKey: ActiveFormOption.CHANGE_PASSWORD,
    },
  ];

  const advancedSettings = [
    {
      label: ActiveFormOption.DELETE_ACCOUNT,
      formKey: ActiveFormOption.DELETE_ACCOUNT,
    },
  ];

  const sections = [
    { title: "General", options: generalSettings },
    { title: "Advanced", options: advancedSettings },
  ];

  return (
    <div className="w-1/2 mt-10 mx-auto">
      <h1 className="page-header">Settings</h1>

      <OptionFormManager
        activeForm={activeForm}
        forms={forms}
        handleClose={closeActiveForm}
      />

      <OptionPanel sections={sections} onSelect={openActiveForm} />
    </div>
  );
};

export default Settings;
