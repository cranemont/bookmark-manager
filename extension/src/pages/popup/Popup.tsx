import {
  Box,
  Button,
  ChakraProvider,
  Flex,
  Heading,
  IconButton,
  Input,
  Select,
  Tag,
  TagCloseButton,
  TagLabel,
  Textarea,
  useDisclosure,
  useToast,
} from "@chakra-ui/react";
import { faTableColumns } from "@fortawesome/free-solid-svg-icons";
import { FaIcon } from "@src/components/common/FaIcon";
import theme from "@src/components/common/GlobalTheme";
import { PopupModal } from "@src/components/popup/PopupModal";
import axios from "axios";
import { useEffect, useRef, useState } from "react";

const Popup = () => {
  const myAxios = axios.create({
    baseURL: "https://nother.ml",
    headers: {
      "Content-Type": "application/json",
    },
    withCredentials: true,
  });

  const [groups, setGroups] = useState([]);
  const [tags, setTags] = useState([]);
  const [inputValue, setInputValue] = useState({
    url: "",
    title: "",
    summary: "",
    group: "",
    tag: "",
  });
  const [modalProps, setModalProps] = useState({
    isLoading: true,
    message: "",
  });

  const { isOpen, onOpen, onClose } = useDisclosure();
  const toast = useToast({
    position: "top",
    duration: 3000,
    isClosable: true,
  });
  const groupSelect = useRef(null);

  const init = async () => {
    // open loading modal
    setModalProps((prev) => ({ ...prev, isLoading: true }));
    onOpen();

    // get current page info and update state
    const [tab] = await chrome.tabs.query({
      active: true,
      lastFocusedWindow: true,
    });
    setInputValue((prev) => ({ ...prev, url: tab.url, title: tab.title }));

    // request APIs
    if (tab.url.startsWith("http://") || tab.url.startsWith("https://")) {
      try {
        // get groups
        const groupsRes = await myAxios.get("groups");

        // get summary
        const summaryRes = await myAxios.post("nlp/summarize", {
          url: tab.url,
        });
        summaryRes.data.tags && setTags(summaryRes.data.tags);
        summaryRes.data.summary &&
          setInputValue((prev) => ({
            ...prev,
            summary: summaryRes.data.summary,
          }));

        // update UI
        setGroups(groupsRes.data);

        // close loading modal
        setModalProps((prev) => ({ ...prev, isLoading: false }));
        onClose();
      } catch (e) {
        if (e.response.status == 403) {
          setModalProps({
            isLoading: false,
            message: "Please go to the panel and login.",
          });
        } else {
          setModalProps({
            isLoading: false,
            message: "Server Error.",
          });
        }
        return;
      }
    } else {
      setModalProps({
        isLoading: false,
        message: "This Website is not supported.",
      });
    }
  };

  const send = async (type: "add-bookmark" | "open-panel", data?) => {
    // check if group is selected
    if (type == "add-bookmark" && !inputValue.group) {
      toast({
        title: "Please select a group",
        status: "error",
      });
      groupSelect.current.focus();
      return;
    }

    // open loading modal
    setModalProps((prev) => ({ ...prev, isLoading: true }));
    onOpen();

    const { status, message } = await chrome.runtime.sendMessage({
      type,
      data,
    });

    // close loading modal
    onClose();

    if (message) {
      toast({
        title: message,
        status: status,
      });
    }
  };

  const onTagInputEnter = (e) => {
    if (e.key === "Enter") {
      if (!tags.includes(inputValue.tag)) {
        setTags([...tags, inputValue.tag]);
      }
      setInputValue((prev) => ({ ...prev, tag: "" }));
    }
  };

  const removeTag = (tagValue: string) => {
    setTags(tags.filter((value) => value != tagValue));
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <ChakraProvider theme={theme}>
      <Box width={96} padding={4} boxSizing="border-box">
        <PopupModal
          isOpen={isOpen}
          onClose={onClose}
          isLoading={modalProps.isLoading}
          message={modalProps.message}
        />
        <Flex alignItems="center" justifyContent="space-between">
          <Heading as="h1" size="md">
            Add Bookmark
          </Heading>
          <IconButton
            aria-label="Panel Link"
            icon={<FaIcon icon={faTableColumns} />}
            variant="ghost"
            onClick={() => send("open-panel")}
            zIndex={9999}
          />
        </Flex>

        <Flex marginY={4} alignItems="center" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Url
          </Heading>
          <Input
            maxWidth={64}
            placeholder="Url"
            onChange={(e) =>
              setInputValue((prev) => ({ ...prev, url: e.target.value }))
            }
            value={inputValue.url}
          />
        </Flex>

        <Flex marginY={4} alignItems="center" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Title
          </Heading>
          <Input
            maxWidth={64}
            placeholder="title"
            onChange={(e) =>
              setInputValue((prev) => ({ ...prev, title: e.target.value }))
            }
            value={inputValue.title}
          />
        </Flex>

        <Flex marginY={4} alignItems="center" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Group
          </Heading>
          <Select
            maxWidth={64}
            placeholder="Select"
            value={inputValue.group}
            onChange={(e) =>
              setInputValue((prev) => ({ ...prev, group: e.target.value }))
            }
            ref={groupSelect}
          >
            {groups.map((value, index) => (
              <option value={value} key={index}>
                {value}
              </option>
            ))}
          </Select>
        </Flex>

        <Flex marginY={4} alignItems="center" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Tags
          </Heading>
          <Input
            maxWidth={64}
            placeholder="Tags"
            onKeyDown={onTagInputEnter}
            onChange={(e) =>
              setInputValue((prev) => ({ ...prev, tag: e.target.value }))
            }
            value={inputValue.tag}
          />
        </Flex>
        <Flex marginY={4} alignItems="center" flexWrap="wrap" gap={1}>
          {tags.map((value, index) => (
            <Tag key={index}>
              <TagLabel>{value}</TagLabel>
              <TagCloseButton onClick={() => removeTag(value)} />
            </Tag>
          ))}
        </Flex>

        <Flex marginY={4} flexDirection="column" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Summary
          </Heading>
          <Textarea
            marginTop={4}
            height={28}
            resize="none"
            onChange={(e) =>
              setInputValue((prev) => ({ ...prev, summary: e.target.value }))
            }
            value={inputValue.summary}
          />
        </Flex>

        <Flex marginTop={4} alignItems="center" justifyContent="space-between">
          <Button
            width="full"
            onClick={() =>
              send("add-bookmark", {
                url: inputValue.url,
                title: inputValue.title,
                summary: inputValue.summary,
                tags: tags,
                group: inputValue.group,
              })
            }
            colorScheme="gray"
          >
            Add
          </Button>
        </Flex>
      </Box>
    </ChakraProvider>
  );
};

export default Popup;
