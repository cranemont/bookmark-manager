import {
  Button,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  ModalProps,
} from "@chakra-ui/react";
import { useState } from "react";

type Props = Omit<ModalProps, "children">;

export const PanelSearchModal = ({
  isOpen,
  onClose,
  search,
  ...props
}: Props & { search: any }) => {
  const [value, setValue] = useState("");

  return (
    <Modal isOpen={isOpen} onClose={onClose} {...props}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Search for bookmarks</ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          <Input
            placeholder="Input keyword here."
            onChange={(e) => setValue(e.target.value)}
            value={value}
          />
        </ModalBody>
        <ModalFooter>
          <Button
            colorScheme="blue"
            mr={3}
            onClick={() => {
              search(value);
              onClose();
            }}
          >
            Search
          </Button>
          <Button onClick={onClose}>Cancel</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};
